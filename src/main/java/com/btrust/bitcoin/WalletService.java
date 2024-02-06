package com.btrust.bitcoin;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class WalletService {
    private final NetworkParameters networkParameters = RegTestParams.get();
    public Wallet generateNewWallet() throws MnemonicException.MnemonicLengthException {
        byte[] random = SecureRandom.getSeed(16);
        List<String> mnemonicCodes = MnemonicCode.INSTANCE.toMnemonic(random);
        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes,null,"",System.currentTimeMillis());
        Wallet wallet = Wallet.fromSeed(networkParameters, seed);
        ECKey key = wallet.freshReceiveKey();
        Address address = LegacyAddress.fromKey(networkParameters,key);
        String privateKeyWIF = key.getPrivateKeyEncoded(networkParameters).toBase58();
        return wallet;
    }


}
