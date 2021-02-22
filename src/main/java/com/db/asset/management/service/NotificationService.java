package com.db.asset.management.service;

import com.db.asset.management.dao.Account;

public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
}
