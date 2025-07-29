import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 基本情報技術者試験のクイズロジック（選択肢形式）を担当するクラス。
 */
public class QuizLogic {
    private final List<String[]> masterQuizList;

    public QuizLogic() {
        masterQuizList = new ArrayList<>();
        // データ形式： [問題文, 正解, 不正解1, 不正解2]
        masterQuizList.add(new String[]{"10進数の10を2進数で表すと？", "1010", "1100", "1001"});
        masterQuizList.add(new String[]{"OSI基本参照モデルで物理層の1つ上（第2層）は？", "データリンク層", "ネットワーク層", "トランスポート層"});
        masterQuizList.add(new String[]{"IPアドレスからMACアドレスを取得するためのプロトコルは？", "ARP", "RARP", "DHCP"});
        masterQuizList.add(new String[]{"Webサーバとの通信で暗号化を行うプロトコルは？(HTTPS)", "TLS", "FTP", "SSH"});
        masterQuizList.add(new String[]{"CPUと主記憶装置の間に置いて処理を高速化する記憶装置は？", "キャッシュメモリ", "レジスタ", "SSD"});
        masterQuizList.add(new String[]{"データベース言語SQLが持つ機能は、データ定義、データ操作とあと一つは？", "データ制御", "データ通信", "データ圧縮"});
        masterQuizList.add(new String[]{"ソフトウェア開発モデルで、工程を順番に進めて後戻りしないモデルは？", "ウォーターフォールモデル", "アジャイルモデル", "スパイラルモデル"});
        masterQuizList.add(new String[]{"システムの構成要素を記号で表現する、UMLの図の一つは？", "クラス図", "フローチャート", "ER図"});
        masterQuizList.add(new String[]{"ネットワークを流れるパケットを監視し、不正アクセスを検知するシステムは？", "IDS", "IPS", "WAF"});
        masterQuizList.add(new String[]{"人間の知的活動によって生み出された財産に関する権利の総称は？", "知的財産権", "所有権", "肖像権"});
        masterQuizList.add(new String[]{"データを送受信する際に、誤りを検出するために付加する符号は？", "パリティビット", "スタートビット", "ストップビット"});
        masterQuizList.add(new String[]{"プログラムの著作権を保持したまま、ソースコードを無償公開する考え方は？", "オープンソース", "フリーウェア", "シェアウェア"});
        masterQuizList.add(new String[]{"コンピュータウイルス対策ソフトが、既知のウイルスの特徴を記録したファイルは？", "パターンファイル", "ログファイル", "設定ファイル"});
        masterQuizList.add(new String[]{"システム開発の見積もり手法で、専門家が経験に基づいて算出する方法は？", "類推見積法", "WBS", "ファンクションポイント法"});
        masterQuizList.add(new String[]{"DNSが持つ、ドメイン名とIPアドレスを対応付ける機能は？", "名前解決", "ルーティング", "暗号化"});
    }

    public List<String[]> getQuizSet() {
        Collections.shuffle(masterQuizList);
        return masterQuizList.subList(0, 5);
    }

    public boolean checkAnswer(String userAnswer, String correctAnswer) {
        return userAnswer != null && userAnswer.equals(correctAnswer);
    }

    /**
     * 正答数に応じて獲得単位を計算する。（改善版）
     * @param correctCount 正解した数
     * @return 獲得単位数
     */
    public int calculateCredits(int correctCount) {
        // ★★★ ここが修正点 ★★★
        if (correctCount == 5) {
            return 8; // 全問正解
        } else if (correctCount == 4) {
            return 6; // 4問正解
        } else if (correctCount == 3) {
            return 4; // 3問正解
        } else if (correctCount == 2) {
            return 2; // 2問正解
        } else {
            return 0; // 0-1問正解
        }
    }
}