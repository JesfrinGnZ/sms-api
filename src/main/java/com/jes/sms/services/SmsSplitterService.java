package com.jes.sms.services;


import java.util.List;

public interface SmsSplitterService {

    List<String> splitMessage(String message);
}
