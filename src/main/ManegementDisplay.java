
package main;

import java.sql.SQLException;

public class ManegementDisplay {

	public static void main(String[] args) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

		//商品(群)の生成
		DBAccess productlist = new DBAccess();         //productlist作る　☆Productlist→DBAccess
		productlist.connectDB();

		boolean flg = true;                                 //boolean型flgを作った 　9選択とかでfalseになったら商品選択に戻る
		while (flg) {                                        //ループ

			productlist.showlist();                           //productlistのshowlist呼び出し　商品選択リスト提示

			int total = productlist.total();                //int型のtotalにproductlistのtotalを入れる　商品のマックス値をとってくる




			//商品選択へ
			int stock;
			Product selected;

				ProductSelectPhase psp = new ProductSelectPhase();           //productselectphase作る

				psp.setMax(total);

				int selectnum = psp.Syohinselect();                                 //　商品番号の入力。エラーはじく。intのselectnumにproductselectphaseのmainを入れる

				selected = productlist.shohinout(selectnum);         //選ばれた商品情報をとってくる　Shohin型のselectedにproductlistのshohinoutを入れる。selectnumを持っていく。

				StockSelectPhase ssp = new StockSelectPhase();

				ssp.setProductlist(productlist);                     //　StockSelectPhaseでもDBAccessを使えるようにする

				stock = selected.getStock();                        //とってきた在庫情報をstockに入れる

			if(stock < 1) {
				ssp.main(stock,selectnum);                           //在庫がない時の処理へ　☆void stock=を消した、selectnum増やした

//				if( selected  instanceof Juice) {
//					Juice juice = (Juice)selected;
//					juice.stockUpdate(stock);
//				}
//				else if(selected instanceof Tea) {
//					Tea tea = (Tea)selected;
//					tea.stockUpdate(stock);
//				}
//
				boolean back = ssp.backWhich();                  //在庫がtrueかfalseかとってくる

				if(back == true) {
				continue;                                        //在庫がtrueある　なら続ける
				}

			}


			//行動選択へ
			ActionSelectPhase asp = new ActionSelectPhase();            //actionselectphaseを作る
			Integer temp = asp.Main();                                  //　お金入れるかどうかの選択。えらーもはじく　integer型のtempにactionselectphaseのメインを入れる。
			switch (temp) {                                             //switch文
			case 1:
				//金額入力へ
				AmountInputPhase aip = new AmountInputPhase();          //　1が選ばれたら、金額投入の処理をしたいから、そこのクラスでDBアクセスが使えるようにする。　amountinputfhaseをつくる
				aip.setProductlist(productlist);

				int amount = selected.getPrice();                      //　金額をとってくる　int型のamountにshohin型のselectedのgetpriceを入れる
				stock = aip.Main(amount,stock,selectnum);                                       //　会計処理　aipのmainに入れる.　stockを1減らして持って帰る

//				selected.stockUpdate(stock);
				// キャスト
//				if( selected  instanceof Juice) {
//					Juice juice = (Juice)selected;
//					juice.stockUpdate(stock);
//				}
//				else if(selected instanceof Tea) {
//					Tea tea = (Tea)selected;
//					tea.stockUpdate(stock);

				//開封画面へ
				OpenSelectPhase osp = new OpenSelectPhase();           //openselectphaseを作る

				String detail = selected.getDetails();                  //　詳細をとってくる　string型のdetailにselectedのgetdetailを入れる
				String comment = selected.getComment();                //　コメントをとってくる　string型のdetailにselectedのgetdetailを入れる
				osp.Main(detail, comment);                             //　表示・選択処理　openselectphaseのmainにdetailとcommentを持っていく
				break;
			case 2:
				continue;                                            //2が選ばれたら、ループ最初へ戻る。
			case 9:
				flg = false;                                         //9が選ばれたら、終わる
				break;
			default:                                                //他の数字ならもう一回聞く
				;
				break;
			}

		}

	}

}
