package com.swipechef.app.data.models

enum class ImageSource {
    PROVIDED,    // image_url from imported recipe or share intent
    OPENGRAPH,   // OpenGraph or oEmbed fetch from source URL, cached
    UPLOADED     // Manual upload from camera or gallery as fallback
}