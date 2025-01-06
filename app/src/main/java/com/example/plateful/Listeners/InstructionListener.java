package com.example.plateful.Listeners;

import com.example.plateful.Models.InstructionResponse;

import java.util.List;

public interface InstructionListener {
    void didFetch(List<InstructionResponse> responses, String message);
    void didError (String message);
}
