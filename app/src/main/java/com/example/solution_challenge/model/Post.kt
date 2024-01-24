package com.example.solution_challenge.model

class Post {
//    to save image in databae
    var postUrl : String=""
    var caption :String=""
    var name:String=""
    constructor()

    constructor(postUrl: String, caption: String, name:String) {
        this.postUrl = postUrl
        this.caption = caption
        this.name = name
    }
}