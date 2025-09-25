-- SwipeChef Supabase Database Schema
-- Import this into your Supabase project

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users table (extends Supabase auth.users)
CREATE TABLE public.users (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    avatar_url TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    last_login_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    CONSTRAINT users_email_unique UNIQUE (email)
);

-- Recipes table
CREATE TABLE public.recipes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    image_url TEXT,
    source_link TEXT,
    cook_time INTEGER, -- in minutes
    prep_time INTEGER, -- in minutes
    servings INTEGER,
    meal_type VARCHAR(50) DEFAULT 'Any',
    tags TEXT[] DEFAULT '{}',
    ratings_average DECIMAL(2,1) DEFAULT 0.0,
    ratings_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Ingredients table (normalized)
CREATE TABLE public.ingredients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    recipe_id UUID NOT NULL REFERENCES public.recipes(id) ON DELETE CASCADE,
    item VARCHAR(255) NOT NULL,
    quantity VARCHAR(100),
    category VARCHAR(50) DEFAULT 'Other',
    order_index INTEGER DEFAULT 0
);

-- Recipe steps table (normalized)
CREATE TABLE public.recipe_steps (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    recipe_id UUID NOT NULL REFERENCES public.recipes(id) ON DELETE CASCADE,
    step_text TEXT NOT NULL,
    step_number INTEGER NOT NULL,
    CONSTRAINT recipe_steps_unique_order UNIQUE (recipe_id, step_number)
);

-- Grocery lists table
CREATE TABLE public.grocery_lists (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    recipe_ids UUID[] DEFAULT '{}',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Grocery list items table
CREATE TABLE public.grocery_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    grocery_list_id UUID NOT NULL REFERENCES public.grocery_lists(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    quantity VARCHAR(100),
    category VARCHAR(50) DEFAULT 'Other',
    completed BOOLEAN DEFAULT FALSE,
    order_index INTEGER DEFAULT 0
);

-- Indexes for performance
CREATE INDEX idx_recipes_user_id ON public.recipes(user_id);
CREATE INDEX idx_recipes_created_at ON public.recipes(created_at DESC);
CREATE INDEX idx_ingredients_recipe_id ON public.ingredients(recipe_id);
CREATE INDEX idx_recipe_steps_recipe_id ON public.recipe_steps(recipe_id);
CREATE INDEX idx_grocery_lists_user_id ON public.grocery_lists(user_id);
CREATE INDEX idx_grocery_items_list_id ON public.grocery_items(grocery_list_id);

-- Row Level Security (RLS) policies
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.recipes ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.ingredients ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.recipe_steps ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.grocery_lists ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.grocery_items ENABLE ROW LEVEL SECURITY;

-- Users can only see/modify their own data
CREATE POLICY "Users can view own profile" ON public.users
    FOR SELECT USING (auth.uid() = id);

CREATE POLICY "Users can update own profile" ON public.users
    FOR UPDATE USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile" ON public.users
    FOR INSERT WITH CHECK (auth.uid() = id);

-- Recipes policies
CREATE POLICY "Users can view own recipes" ON public.recipes
    FOR SELECT USING (auth.uid() = user_id);

CREATE POLICY "Users can insert own recipes" ON public.recipes
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own recipes" ON public.recipes
    FOR UPDATE USING (auth.uid() = user_id);

CREATE POLICY "Users can delete own recipes" ON public.recipes
    FOR DELETE USING (auth.uid() = user_id);

-- Ingredients policies (inherit from recipes)
CREATE POLICY "Users can view ingredients of own recipes" ON public.ingredients
    FOR SELECT USING (
        EXISTS (
            SELECT 1 FROM public.recipes
            WHERE recipes.id = ingredients.recipe_id
            AND recipes.user_id = auth.uid()
        )
    );

CREATE POLICY "Users can modify ingredients of own recipes" ON public.ingredients
    FOR ALL USING (
        EXISTS (
            SELECT 1 FROM public.recipes
            WHERE recipes.id = ingredients.recipe_id
            AND recipes.user_id = auth.uid()
        )
    );

-- Recipe steps policies (inherit from recipes)
CREATE POLICY "Users can view steps of own recipes" ON public.recipe_steps
    FOR SELECT USING (
        EXISTS (
            SELECT 1 FROM public.recipes
            WHERE recipes.id = recipe_steps.recipe_id
            AND recipes.user_id = auth.uid()
        )
    );

CREATE POLICY "Users can modify steps of own recipes" ON public.recipe_steps
    FOR ALL USING (
        EXISTS (
            SELECT 1 FROM public.recipes
            WHERE recipes.id = recipe_steps.recipe_id
            AND recipes.user_id = auth.uid()
        )
    );

-- Grocery lists policies
CREATE POLICY "Users can view own grocery lists" ON public.grocery_lists
    FOR SELECT USING (auth.uid() = user_id);

CREATE POLICY "Users can insert own grocery lists" ON public.grocery_lists
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own grocery lists" ON public.grocery_lists
    FOR UPDATE USING (auth.uid() = user_id);

CREATE POLICY "Users can delete own grocery lists" ON public.grocery_lists
    FOR DELETE USING (auth.uid() = user_id);

-- Grocery items policies (inherit from grocery lists)
CREATE POLICY "Users can view items of own grocery lists" ON public.grocery_items
    FOR SELECT USING (
        EXISTS (
            SELECT 1 FROM public.grocery_lists
            WHERE grocery_lists.id = grocery_items.grocery_list_id
            AND grocery_lists.user_id = auth.uid()
        )
    );

CREATE POLICY "Users can modify items of own grocery lists" ON public.grocery_items
    FOR ALL USING (
        EXISTS (
            SELECT 1 FROM public.grocery_lists
            WHERE grocery_lists.id = grocery_items.grocery_list_id
            AND grocery_lists.user_id = auth.uid()
        )
    );

-- Functions for updated_at timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers for updated_at
CREATE TRIGGER update_recipes_updated_at BEFORE UPDATE ON public.recipes
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_grocery_lists_updated_at BEFORE UPDATE ON public.grocery_lists
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Storage bucket for recipe images
INSERT INTO storage.buckets (id, name, public) VALUES ('recipe-images', 'recipe-images', true);

-- Storage policies for recipe images
CREATE POLICY "Users can upload recipe images" ON storage.objects
    FOR INSERT WITH CHECK (
        bucket_id = 'recipe-images'
        AND auth.uid()::text = (storage.foldername(name))[1]
    );

CREATE POLICY "Users can view recipe images" ON storage.objects
    FOR SELECT USING (bucket_id = 'recipe-images');

CREATE POLICY "Users can update own recipe images" ON storage.objects
    FOR UPDATE USING (
        bucket_id = 'recipe-images'
        AND auth.uid()::text = (storage.foldername(name))[1]
    );

CREATE POLICY "Users can delete own recipe images" ON storage.objects
    FOR DELETE USING (
        bucket_id = 'recipe-images'
        AND auth.uid()::text = (storage.foldername(name))[1]
    );