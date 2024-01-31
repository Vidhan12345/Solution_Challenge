package com.example.solution_challenge.model

class Donate {
    var name = " "
    var imgUrl= " "
    var location = " "
    var description = " "
    var number = ""
    constructor()

    constructor(name:String,imgUrl:String,location:String,description:String,number:String){
        this.name = name
        this.imgUrl = imgUrl
        this.location = location
        this.description = description
        this.number = number
    }

}