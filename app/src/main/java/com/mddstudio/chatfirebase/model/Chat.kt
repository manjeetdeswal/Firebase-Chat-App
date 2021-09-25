package com.mddstudio.chatfirebase.model

class Chat {
    var message:String? =null
    var sender :String? =null
    constructor(){}
    constructor(message: String?, sender: String?) {
        this.message = message
        this.sender = sender
    }
}