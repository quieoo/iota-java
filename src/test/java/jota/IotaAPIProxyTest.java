package jota;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jota.dto.response.*;
import jota.error.ArgumentException;
import jota.error.NotEnoughBalanceException;
import jota.model.Inputs;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Let's do some integration test coverage against a default local real node.
 *
 * @author davassi
 */
public class IotaAPIProxyTest {

    private static Gson gson = new GsonBuilder().create();

    private static final String TEST_SEED = "AAA999999999999999999999999999999999999999999999999999999999999999999999999999999";
    private static final String TEST_ADDRESS_WITHOUT_CHECKSUM = "PNGMCSNRCTRHCHPXYTPKEJYPCOWKOMRXZFHH9N9VDIKMNVAZCMIYRHVJIAZARZTUETJVFDMBEBIQE9QTH";
    private static final String TEST_ADDRESS_WITH_CHECKSUM = "PNGMCSNRCTRHCHPXYTPKEJYPCOWKOMRXZFHH9N9VDIKMNVAZCMIYRHVJIAZARZTUETJVFDMBEBIQE9QTHBFWDAOEFA";
    private static final String TEST_HASH = "OAATQS9VQLSXCLDJVJJVYUGONXAXOFMJOZNSYWRZSWECMXAQQURHQBJNLD9IOFEPGZEPEMPXCIVRX9999";
    private static final String TEST_TRYTES = "BYSWEAUTWXHXZ9YBZISEK9LUHWGMHXCGEVNZHRLUWQFCUSDXZHOFHWHL9MQPVJXXZLIXPXPXF9KYEREFSKCPKYIIKPZVLHUTDFQKKVVBBN9ATTLPCNPJDWDEVIYYLGPZGCWXOBDXMLJC9VO9QXTTBLAXTTBFUAROYEGQIVB9MJWJKXJMCUPTWAUGFZBTZCSJVRBGMYXTVBDDS9MYUJCPZ9YDWWQNIPUAIJXXSNLKUBSCOIJPCLEFPOXFJREXQCUVUMKSDOVQGGHRNILCO9GNCLWFM9APMNMWYASHXQAYBEXF9QRIHIBHYEJOYHRQJAOKAQ9AJJFQ9WEIWIJOTZATIBOXQLBMIJU9PCGBLVDDVFP9CFFSXTDUXMEGOOFXWRTLFGV9XXMYWEMGQEEEDBTIJ9OJOXFAPFQXCDAXOUDMLVYRMRLUDBETOLRJQAEDDLNVIRQJUBZBO9CCFDHIX9MSQCWYAXJVWHCUPTRSXJDESISQPRKZAFKFRULCGVRSBLVFOPEYLEE99JD9SEBALQINPDAZHFAB9RNBH9AZWIJOTLBZVIEJIAYGMC9AZGNFWGRSWAXTYSXVROVNKCOQQIWGPNQZKHUNODGYADPYLZZZUQRTJRTODOUKAOITNOMWNGHJBBA99QUMBHRENGBHTH9KHUAOXBVIVDVYYZMSEYSJWIOGGXZVRGN999EEGQMCOYVJQRIRROMPCQBLDYIGQO9AMORPYFSSUGACOJXGAQSPDY9YWRRPESNXXBDQ9OZOXVIOMLGTSWAMKMTDRSPGJKGBXQIVNRJRFRYEZ9VJDLHIKPSKMYC9YEGHFDS9SGVDHRIXBEMLFIINOHVPXIFAZCJKBHVMQZEVWCOSNWQRDYWVAIBLSCBGESJUIBWZECPUCAYAWMTQKRMCHONIPKJYYTEGZCJYCT9ABRWTJLRQXKMWY9GWZMHYZNWPXULNZAPVQLPMYQZCYNEPOCGOHBJUZLZDPIXVHLDMQYJUUBEDXXPXFLNRGIPWBRNQQZJSGSJTTYHIGGFAWJVXWL9THTPWOOHTNQWCNYOYZXALHAZXVMIZE9WMQUDCHDJMIBWKTYH9AC9AFOT9DPCADCV9ZWUTE9QNOMSZPTZDJLJZCJGHXUNBJFUBJWQUEZDMHXGBPTNSPZBR9TGSKVOHMOQSWPGFLSWNESFKSAZY9HHERAXALZCABFYPOVLAHMIHVDBGKUMDXC9WHHTIRYHZVWNXSVQUWCR9M9RAGMFEZZKZ9XEOQGOSLFQCHHOKLDSA9QCMDGCGMRYJZLBVIFOLBIJPROKMHOYTBTJIWUZWJMCTKCJKKTR9LCVYPVJI9AHGI9JOWMIWZAGMLDFJA9WU9QAMEFGABIBEZNNAL9OXSBFLOEHKDGHWFQSHMPLYFCNXAAZYJLMQDEYRGL9QKCEUEJ9LLVUOINVSZZQHCIKPAGMT9CAYIIMTTBCPKWTYHOJIIY9GYNPAJNUJ9BKYYXSV9JSPEXYMCFAIKTGNRSQGUNIYZCRT9FOWENSZQPD9ALUPYYAVICHVYELYFPUYDTWUSWNIYFXPX9MICCCOOZIWRNJIDALWGWRATGLJXNAYTNIZWQ9YTVDBOFZRKO9CFWRPAQQRXTPACOWCPRLYRYSJARRKSQPR9TCFXDVIXLP9XVL99ERRDSOHBFJDJQQGGGCZNDQ9NYCTQJWVZIAELCRBJJFDMCNZU9FIZRPGNURTXOCDSQGXTQHKHUECGWFUUYS9J9NYQ9U9P9UUP9YMZHWWWCIASCFLCMSKTELZWUGCDE9YOKVOVKTAYPHDF9ZCCQAYPJIJNGSHUIHHCOSSOOBUDOKE9CJZGYSSGNCQJVBEFTZFJ9SQUHOASKRRGBSHWKBCBWBTJHOGQ9WOMQFHWJVEG9NYX9KWBTCAIXNXHEBDIOFO9ALYMFGRICLCKKLG9FOBOX9PDWNQRGHBKHGKKRLWTBEQMCWQRLHAVYYZDIIPKVQTHYTWQMTOACXZOQCDTJTBAAUWXSGJF9PNQIJ9AJRUMUVCPWYVYVARKR9RKGOUHHNKNVGGPDDLGKPQNOYHNKAVVKCXWXOQPZNSLATUJT9AUWRMPPSWHSTTYDFAQDXOCYTZHOYYGAIM9CELMZ9AZPWB9MJXGHOKDNNSZVUDAGXTJJSSZCPZVPZBYNNTUQABSXQWZCHDQSLGK9UOHCFKBIBNETK999999999999999999999999999999999999999999999999999999999999999999999999999999999NOXDXXKUDWLOFJLIPQIBRBMGDYCPGDNLQOLQS99EQYKBIU9VHCJVIPFUYCQDNY9APGEVYLCENJIOBLWNB999999999XKBRHUD99C99999999NKZKEKWLDKMJCI9N9XQOLWEPAYWSH9999999999999999999999999KDDTGZLIPBNZKMLTOLOXQVNGLASESDQVPTXALEKRMIOHQLUHD9ELQDBQETS9QFGTYOYWLNTSKKMVJAUXSIROUICDOXKSYZTDPEDKOQENTJOWJONDEWROCEJIEWFWLUAACVSJFTMCHHXJBJRKAAPUDXXVXFWP9X9999IROUICDOXKSYZTDPEDKOQENTJOWJONDEWROCEJIEWFWLUAACVSJFTMCHHXJBJRKAAPUDXXVXFWP9X9999";
    private static final String TEST_MILESTONE = "SMYMAKKPSUKCKDRUEYCGZJTYCZ9HHDMDUWBAPXARGURPQRHTAJDASRWMIDTPTBNDKDEFBUTBGGAFX9999";
    private static final Integer TEST_MILESTONE_INDEX = 8059;

    private IotaAPIProxy proxy;

    @Before
    public void createProxyInstance() {
        proxy = new IotaAPIProxy.Builder().build();
    }

    @Test
    public void shouldGetNodeInfo() {
        GetNodeInfoResponse nodeInfo = proxy.getNodeInfo();
        assertThat(nodeInfo.getAppVersion(), IsNull.notNullValue());
    }

    @Test
    public void shouldGetNeighbors() {
        GetNeighborsResponse neighbors = proxy.getNeighbors();
        assertThat(neighbors.getNeighbors(), IsNull.notNullValue());
    }

    @Test
    public void shouldAddNeighbors() {
        AddNeighborsResponse res = proxy.addNeighbors("udp://8.8.8.8:14265");
        assertThat(res, IsNull.notNullValue());
    }

    @Test
    public void shouldRemoveNeighbors() {
        RemoveNeighborsResponse res = proxy.removeNeighbors("udp://8.8.8.8:14265");
        assertThat(res, IsNull.notNullValue());
    }

    @Test
    public void shouldGetTips() {
        GetTipsResponse tips = proxy.getTips();
        assertThat(tips, IsNull.notNullValue());
    }

    @Test
    public void shouldFindTransactionsByAddresses() {
        FindTransactionResponse trans = proxy.findTransactionsByAddresses(TEST_ADDRESS_WITH_CHECKSUM);
        System.err.println(gson.toJson(trans));
        assertThat(trans, IsNull.notNullValue());
    }

    @Test
    public void shouldFindTransactionsByApprovees() {
        FindTransactionResponse trans = proxy.findTransactionsByApprovees(new String[]{TEST_HASH});
        assertThat(trans, IsNull.notNullValue());
    }

    @Test
    public void shouldFindTransactionsByBundles() {
        FindTransactionResponse trans = proxy.findTransactionsByBundles(TEST_HASH);
        assertThat(trans, IsNull.notNullValue());
    }

    @Test
    public void shouldFindTransactionsByDigests() {
        FindTransactionResponse trans = proxy.findTransactionsByDigests(TEST_HASH);
        assertThat(trans, IsNull.notNullValue());
    }


    // ###

    @Test
    public void shouldGetTrytes() {
        GetTrytesResponse res = proxy.getTrytes(TEST_HASH);
        assertThat(res, IsNull.notNullValue());

    }

    @Test
    public void shouldGetInclusionStates() {
        GetInclusionStateResponse res = proxy.getInclusionStates(new String[]{TEST_ADDRESS_WITH_CHECKSUM}, new String[]{"DNSBRJWNOVUCQPILOQIFDKBFJMVOTGHLIMLLRXOHFTJZGRHJUEDAOWXQRYGDI9KHYFGYDWQJZKX999999"});
        assertThat(res, IsNull.notNullValue());
    }

    @Test // very long execution
    public void shouldGetTransactionsToApprove() {
        GetTransactionsToApproveResponse res = proxy.getTransactionsToApprove(27);
        assertThat(res, IsNull.notNullValue());
    }

    @Test
    public void shouldGetBalances() {
        GetBalancesResponse res = proxy.getBalances(100, new String[]{TEST_ADDRESS_WITH_CHECKSUM});
        System.err.println(res);
        assertThat(res, IsNull.notNullValue());
    }

    @Test
    public void shouldCreateIotaApiProxyInstanceWithDefaultValues() {
        IotaAPIProxy proxy = new IotaAPIProxy.Builder().build();
        assertThat(proxy, IsNull.notNullValue());
    }

    @Test
    public void shouldCreateANewAddress() {
        final GetNewAddressResponse res = proxy.getNewAddress(TEST_SEED, 0, false, 1, false);
        assertThat(res.getAddresses(), Is.is(Collections.singletonList(TEST_ADDRESS_WITHOUT_CHECKSUM)));
    }

    @Test
    public void shouldGetGetInputs() throws ArgumentException, NotEnoughBalanceException {
        final Inputs res = proxy.getInputs(TEST_SEED, 0, 1, 1);
        assertTrue(res.getTotalBalance() > 0);
    }
}