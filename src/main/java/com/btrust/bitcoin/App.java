package com.btrust.bitcoin;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InsufficientMoneyException, MnemonicException.MnemonicLengthException, BlockStoreException, PrunedException {


        WalletService walletService = new WalletService();
        Wallet wallet = walletService.generateNewWallet();
        System.out.println("Wallet Balance : "+ wallet.getBalance());
        System.out.println("Wallet used for transaction created");


        String lockHex = "427472757374204275696c64657273";
        ScriptService scriptService = new ScriptService(lockHex);


        String redeemScriptHex = scriptService.getRedeemScriptHex();
        String outputAddress = scriptService.getAddressString();


        System.out.println("Redeem Script  : " + redeemScriptHex);
        System.out.println("Output Address : " + outputAddress);




//        Transaction transaction = scriptService.constructTransaction(outputAddress, Coin.valueOf(100_000));
//        System.out.println("transaction    : " + transaction.toString());

    }



}
