package com.example.mvvm.viewmodel

import com.example.mvvm.model.MessageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MessageViewModel {
    private var _message = MutableStateFlow(MessageModel(content = ""));
    private val message: StateFlow<MessageModel> = _message.asStateFlow()
    // val message by MessageViewModel.collectAsState()

    fun write(text: String) {
        _message.update { messageModel -> messageModel.copy(content = text) }
    }
}