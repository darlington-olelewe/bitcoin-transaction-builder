package com.btrust.bitcoin;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletTransaction;

public class ScriptService {

    private WalletService walletService = null;
    private NetworkParameters networkParameters = null;

    private final String lockHex;

    private final String redeemScript;

    private final String addressString;

    public ScriptService(String lockHex){
        this.walletService = new WalletService();
        this.networkParameters = RegTestParams.get();
        this.lockHex = lockHex;
        this.redeemScript = generateRedeemScript();
        this.addressString = deriveAddressFromRedeemScript(this.redeemScript);
    }
    private String generateRedeemScript() {
        Script redeemScript = new ScriptBuilder()
                .op(ScriptOpCodes.OP_SHA256)
                .data(Utils.HEX.decode(this.lockHex))
                .op(ScriptOpCodes.OP_EQUAL)
                .build();
        return Utils.HEX.encode(redeemScript.getProgram());
    }

    public String getRedeemScriptHex(){
        return this.redeemScript;
    }

    public String getAddressString(){
        return this.addressString;
    }
    private String deriveAddressFromRedeemScript(String redeemScriptHex) {
        Script redeemScript = new Script(Utils.HEX.decode(redeemScriptHex));
        byte[] scriptHash = Utils.sha256hash160(redeemScript.getProgram());
        Address address = LegacyAddress.fromScriptHash(this.networkParameters,scriptHash);
        return address.toString();
    }

    public Transaction constructTransaction(String outputAddress, Coin amount) throws MnemonicException.MnemonicLengthException, InsufficientMoneyException {
        Wallet wallet = this.walletService.generateNewWallet();
        Address destination = Address.fromString(networkParameters, outputAddress);
        Transaction send = wallet.createSend(destination, amount);
        System.out.println(send);
        return send;
    }
//
//    public static Transaction spendFromTransaction(Transaction tx, String unlockingScriptHex, String changeAddress, Coin changeAmount) {
//        Script unlockingScript = new Script(Utils.HEX.decode(unlockingScriptHex));
//        TransactionInput input = tx.getInput(0);
//        input.setScriptSig(unlockingScript);
//
//        Address change = Address.fromString(MainNetParams.get(), changeAddress);
//        TransactionOutput changeOutput = new TransactionOutput(MainNetParams.get(), tx, changeAmount, change);
//        tx.addOutput(changeOutput);
//
//        return tx;
//    }

//    public static void main(String[] args) {
//        String lockHex = "427472757374204275696c64657273";
//        String redeemScriptHex = generateRedeemScript(lockHex);
//        System.out.println(redeemScriptHex);
//
//        String outputAddress = deriveAddressFromRedeemScript(redeemScriptHex);
//        System.out.println("Redeem Script: " + redeemScriptHex);
//        System.out.println("Output Address: " + outputAddress);

//        Coin amount = Coin.valueOf(100_000);  // Amount in satoshis
//        Transaction tx = constructTransaction(outputAddress, amount);
//        System.out.println("Constructed Transaction: " + tx.toString());
//
//        String unlockingScriptHex = "INSERT_UNLOCKING_SCRIPT_HEX_HERE";
//        String changeAddress = "INSERT_CHANGE_ADDRESS_HERE";
//        Coin changeAmount = Coin.valueOf(50_000);  // Change amount in satoshis
//
//        tx = spendFromTransaction(tx, unlockingScriptHex, changeAddress, changeAmount);
//        System.out.println("Spending Transaction: " + tx.toString());
//    }
}

