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


//        WalletAppKit kit = new WalletAppKit(RegTestParams.get(), new java.io.File("/home/xpress/.bitcoin/regtest/wallets/xpress"), "walletFileName");
//        kit.startAsync();
//        kit.awaitRunning();
//
//        // Access the wallet
//        Wallet wallet = kit.wallet();
//
//        // Print the current balance (optional)
//        System.out.println("Current Wallet Balance: " + wallet.getBalance());
//
//        // Load the wallet with 10 bitcoins
//        wallet.importKey(new ECKey());  // Import a new key to the wallet
//        wallet.importKey(new ECKey());  // Import another key (repeat as needed)
//
//        // Print the updated balance (optional)
//        System.out.println("Updated Wallet Balance: " + wallet.getBalance());
//
//        // Save the wallet (optional)
//        kit.stopAsync();
//        kit.awaitTerminated();
        WalletService walletService = new WalletService();
        String lockHex = "427472757374204275696c64657273";
        ScriptService scriptService = new ScriptService(lockHex);


        String redeemScriptHex = scriptService.getRedeemScriptHex();
        String outputAddress = scriptService.getAddressString();


        System.out.println("Redeem Script  : " + redeemScriptHex);
        System.out.println("Output Address : " + outputAddress);

        Wallet wallet = walletService.generateNewWallet();
        System.out.println(wallet.getBalance());
        mineBlocks(wallet);
        addBitcoins(wallet,10);
        System.out.println(wallet.getBalance());

//        Transaction transaction = scriptService.constructTransaction(outputAddress, Coin.valueOf(100_000));
//        System.out.println("transaction    : " + transaction.toString());

    }


    private static void mineBlocks(Wallet wallet) throws BlockStoreException, PrunedException {



        // Create a block store
        BlockStore blockStore = new MemoryBlockStore(RegTestParams.get());

        // Create a block chain
        BlockChain blockChain = new BlockChain(RegTestParams.get(), blockStore);

        // Mine some blocks (101 blocks to fund the wallet)
        for (int i = 0; i < 101; i++) {
            Block block = blockStore.getChainHead().getHeader().createNextBlock(wallet.currentReceiveAddress());
            blockChain.add(block);
        }
    }

    private static void addBitcoins(Wallet wallet, long amount) throws InsufficientMoneyException {
        // Add bitcoins to the wallet
//        Transaction tx = new Transaction(wallet.getParams());
//        tx.addOutput(Coin.valueOf(amount * 100_000_000), wallet.currentReceiveAddress());
//        wallet.receivePending(tx,null,true);

        SendRequest request = SendRequest.to(wallet.currentReceiveAddress(),Coin.valueOf(amount * 100_000_000));
        wallet.completeTx(request);
        wallet.commitTx(request.tx);
    }
}
