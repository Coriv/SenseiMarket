package com.sensei.errorHandler;

import com.sensei.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler {

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<Object> userNotFoundExceptionHandler(InvalidUserIdException e) {
        return new ResponseEntity<>("User with given ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CryptocurrencyNotFoundException.class)
    public ResponseEntity<Object> cryptocurrencyNotFoundHandler(CryptocurrencyNotFoundException e) {
        return new ResponseEntity<>("Cryptocurrency with given SYMBOL does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CryptoIsObjectOfTradingException.class)
    public ResponseEntity<Object> cryptoIsObjectOfTradingHandler(CryptoIsObjectOfTradingException e) {
        return new ResponseEntity<>("There are open trades with the cryptocurrency." +
                "To remove first close open transactions", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyCryptocurrencyDatabaseException.class)
    public ResponseEntity<Object> emptyCryptoDatabaseHandler(EmptyCryptocurrencyDatabaseException e) {
        return new ResponseEntity<>("There are no cryptocurrencies to trade", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotEmptyWalletException.class)
    public ResponseEntity<Object> notEmptyWalletHandler(NotEmptyWalletException e) {
        return new ResponseEntity<>("Wallet still have founds. Withdraw found first to remove wallet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<Object> notEnoughFoundHandler(NotEnoughFoundsException e) {
        return new ResponseEntity<>("You don't have sufficient found to complete this transaction", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TradeNotFoundException.class)
    public ResponseEntity<Object> tradeNotFoundHandler(TradeNotFoundException e) {
        return new ResponseEntity<>("Trade with given ID does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownErrorException.class)
    public ResponseEntity<Object> unknownErrorHandler(UnknownErrorException e) {
        return new ResponseEntity<>("Something went wrong." +
                " If the problem persist, please contact support", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotVerifyException.class)
    public ResponseEntity<Object> userNotVerifyHandler(UserNotVerifyException e) {
        return new ResponseEntity<>("Verify user PESEL and ID card first to create wallet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletAlreadyExistException.class)
    public ResponseEntity<Object> walletAlreadyExistHandler(WalletAlreadyExistException e) {
        return new ResponseEntity<>("Wallet already exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletCryptoNotFoundException.class)
    public ResponseEntity<Object> walletCryptoNotFoundHandler(WalletCryptoNotFoundException e) {
        return new ResponseEntity<>("WalletCrypto with given ID doest not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> walletNotFoundHandler(WalletNotFoundException e) {
        return new ResponseEntity<>("Wallet with given ID doest not exist", HttpStatus.NOT_FOUND);
    }
}
