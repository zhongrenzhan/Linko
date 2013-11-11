package cn.com.unifront.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.com.unifront.domain.AccountBalanceInfo;
import cn.com.unifront.domain.AccountName;
import cn.com.unifront.domain.CategoryName;
import cn.com.unifront.domain.CurrenceName;
import cn.com.unifront.domain.PayIncomeList;
import cn.com.unifront.domain.PayOutName;

public class SqlOperate {
	private Context context;
	private DbOpenHelper dbOpenHelper;
	private List<CategoryName> arry;
	private List<PayOutName> payoutarry;
	private List<AccountName> accountarry;
	private List<CurrenceName> Currencetarry;
	private List<AccountBalanceInfo> BalanceInfoes;

	public SqlOperate(Context context) {
		this.context = context;
		dbOpenHelper = new DbOpenHelper(context);
	}

	public String categoryname[] = { "工资", "基金", "分红", "利息", "补贴", "奖金", "提成",
			"基金" };
	public String paycategory[] = { "话费", "人情往来", "休闲娱乐", "交通费用", "衣服", "日用品" };
	public String account[] = { "现金", "存款", "信用卡", "支付宝" };
	public String sign[] = { "CNY", "USD", "HKD", "MOP", "TWD", "JPY", "KRW",
			"EUR", "PHP", "SGD" };
	public String currency[] = { "人民币", "美元", "港元", "澳元", "新台币", "日元", "韩元",
			"欧元", "菲律宾索", "新加坡元" };
	private String tag = "有没有获取到？";

	/**
	 * 初始化数据库的函数
	 */
	public void initDb() {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

		if (db.isOpen()) {
			// 初始化类别
			for (int i = 0; i < categoryname.length - 1; i++) {
				ContentValues cv = new ContentValues();
				cv.put("name", categoryname[i]);
				db.insert("incomecategory", null, cv);
			}

			// 初始化账户名
			for (int j = 0; j < account.length - 1; j++) {
				ContentValues cv = new ContentValues();
				cv.put("name", account[j]);
				db.insert("account", "0", cv);
			}

			// 初始化货币类型
			for (int k = 0; k < sign.length - 1; k++) {
				ContentValues cv = new ContentValues();
				cv.put("sign", sign[k]);
				cv.put("name", currency[k]);
				db.insert("currency", "0", cv);

			}

			// 初始支出类型
			for (int k = 0; k < paycategory.length - 1; k++) {
				ContentValues cv = new ContentValues();
				cv.put("name", paycategory[k]);
				db.insert("payoutcategory", "0", cv);

			}

		}
		db.close();
	}

	/**
	 * 添加收入种类的函数
	 */
	public void insertCategory(String category, String account, String remark) {
		ContentValues cv = new ContentValues();
		cv.put("name", category);
		cv.put("account_id", account);
		cv.put("remark", remark);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.insert("incomecategory", null, cv);
			db.close();
		}
	}
	
	/**
	 * 添加支出种类的函数
	 */
	public void insertPayCategory(String category, String account, String remark) {
		ContentValues cv = new ContentValues();
		cv.put("name", category);
		cv.put("account_id", account);
		cv.put("remark", remark);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.insert("payoutcategory", null, cv);
			db.close();
		}
	}

	/**
	 * 查询种类的函数
	 */
	public List<CategoryName> getCategry(String table) {
		String tableName = table;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(tableName, null, null, null, null,
				null, "amount desc");
		arry = new ArrayList<CategoryName>();

		while (cursor.moveToNext()) {
			CategoryName name = new CategoryName();
			int index = cursor.getColumnIndex("name");
			String category = cursor.getString(index);
			name.setName(category);
			int indexOfAmount = cursor.getColumnIndex("amount");
			String amount = cursor.getString(indexOfAmount);
			name.setAmount(amount);
			Log.i(tag, category + "");
			arry.add(name);
		}
		cursor.close();
		db.close();
		return arry;
	}
	
	/**
	 * 查询种类的函数,chart专用,如果amount为0则不添加到集合中
	 */
	public List<CategoryName> getCategry_1(String table) {
		String tableName = table;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(tableName, null, null, null, null,
				null, "amount desc");
		arry = new ArrayList<CategoryName>();

		while (cursor.moveToNext()) {
			CategoryName name = new CategoryName();
			int index = cursor.getColumnIndex("name");
			String category = cursor.getString(index);
			name.setName(category);
			int indexOfAmount = cursor.getColumnIndex("amount");
			String amount = cursor.getString(indexOfAmount);
			name.setAmount(amount);
			Log.i(tag, category + "");
			if(amount.equals("0")==false){
				arry.add(name);
			}
			
		}
		cursor.close();
		db.close();
		return arry;
	}

	

	/**
	 * 查询收入账户的函数
	 * 
	 * @return
	 */
	public List<AccountName> getAllAccount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("account", null, null, null, null, null, null);
		accountarry = new ArrayList<AccountName>();

		while (cursor.moveToNext()) {
			AccountName name = new AccountName();
			int index = cursor.getColumnIndex("name");
			String account = cursor.getString(index);
			name.setName(account);
			Log.i(tag, account + "");
			accountarry.add(name);

		}
		cursor.close();
		db.close();
		return accountarry;
	}

	/**
	 * 查询货币类型的函数
	 * 
	 * @return
	 */
	public List<CurrenceName> getAllCurrence() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db
				.query("currency", null, null, null, null, null, null);
		Currencetarry = new ArrayList<CurrenceName>();

		while (cursor.moveToNext()) {
			CurrenceName name = new CurrenceName();
			int index = cursor.getColumnIndex("name");
			String account = cursor.getString(index);
			name.setName(account);
			Log.i(tag, account + "");
			Currencetarry.add(name);
		}
		cursor.close();
		db.close();
		return Currencetarry;
	}

	/**
	 * 添加账户的函数
	 */
	public void insertaccount(String account, String balance, String warmmoney,
			String currence) {
		ContentValues cv = new ContentValues();
		cv.put("name", account);
		cv.put("balance", balance);
		cv.put("warm_balance", warmmoney);
		cv.put("currency_id", currence);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.insert("account", null, cv);
			db.close();
		}
	}

	/**
	 * 插入收入页面得到的数据
	 */
	public void insertPayIncomeDate(int type,float amount, String time,
			String Account_Id, String incomecategory_Id, String describe) {

		ContentValues cv = new ContentValues();
		cv.put("type", type);
		cv.put("amount", amount);
		cv.put("time", time);
		cv.put("account", Account_Id);
		cv.put("incomecategory", incomecategory_Id);
		cv.put("describe", describe);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.insert("pay_income", null, cv);
			db.close();
		}
	}

	public void deleteCategory(String tab, String where) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(tab, "name=?", new String[] { where });
			db.close();
		}
	}
	
	/**
	 * 查询账户余额
	 */
	public  List<AccountBalanceInfo> getAccountBalanceInfo() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db
				.query("account", null, null, null, null, null, "balance desc");
		BalanceInfoes = new ArrayList<AccountBalanceInfo>();

		while (cursor.moveToNext()) {
			AccountBalanceInfo info = new AccountBalanceInfo();
			int index = cursor.getColumnIndex("name");
			String account = cursor.getString(index);
			info.setName(account);
			int indexBalance = cursor.getColumnIndex("balance");
			String balance = cursor.getString(indexBalance);
			info.setBalance(balance);
			BalanceInfoes.add(info);
		}
		cursor.close();
		db.close();
		return BalanceInfoes;
	}
	
	/**
	 * 更新账户金额
	 */
	public  void updateAccountBalance(){	
		List<AccountName> accounts = getAllAccount();
		int size = accounts.size();
		for(int i =0;i<size;i++){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			String name = accounts.get(i).getName();
			String sql = "update account set balance = (select ifnull(sum(amount),0) from pay_income where account = '"+name+"') where name = '"+name+"'";
			db.execSQL(sql);
			db.close();
		}		
		
	}
	
	/**
	 * 按条件查询详细收支
	 */
	public  List<PayIncomeList> getPayIncomeList(String[] condictions){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		List<PayIncomeList> infoLists;
		Cursor cursor;
		 String[]  condiction = condictions;
		int size =  condiction.length;
		for(int i=0;i<size;i++){
			Log.i("传来数据数组的：", condiction[i].length()+"===");
		}
		if(condiction[4].length()<1&&condiction[5].length()<1){
			String sql = "select in_pay_Id as _id,amount,time , account from pay_income where  time<=? and time>=? and account =?";
			cursor = db.rawQuery(sql, new String []{condiction[3],condiction[2],condiction[1]});
			
		}else if(condiction[4].length()<1&&condiction[5].length()>1){
			String sql = "select in_pay_Id as _id,amount,time , account from pay_income where amount <=? and amount>=? and  time<=? and time>=? and account =?";
			cursor =  db.rawQuery(sql, new String []{condiction[5],condiction[3],condiction[2],condiction[1]});
		
		}else if(condiction[4].length()>1&&condiction[5].length()<1){
			String sql = "select in_pay_Id as _id,amount,time , account from pay_income where amount >=?  and  time<=? and time>=? and account =?";
			cursor =  db.rawQuery(sql, new String []{condiction[4],condiction[3],condiction[2],condiction[1]});
		
		}else{
			String sql = "select in_pay_Id as _id,amount,time , account from pay_income where amount <=?  and amount >=? and time<=? and time>=? and account =?";
			cursor =  db.rawQuery(sql, new String []{condiction[5],condiction[4],condiction[3],condiction[2],condiction[1]});
		}
		
		infoLists = new ArrayList<PayIncomeList>();
		while (cursor.moveToNext()) {
			PayIncomeList listInfo = new PayIncomeList();
			int index = cursor.getColumnIndex("amount");
			String amount = cursor.getString(index);
			listInfo.setAmount(amount);
			
			int indexTime = cursor.getColumnIndex("time");
			String time = cursor.getString(indexTime);
			listInfo.setDate(time);	
			
			int indexAccount = cursor.getColumnIndex("account");
			String account = cursor.getString(indexAccount);
			listInfo.setAccount(account);			
			infoLists.add(listInfo);
		}
		cursor.close();
		db.close();
		return infoLists;
		
	}
	
	/**
	 * 更新收入类型金额
	 */
	public  void updateCategryAmount(String table){	
		String tableName = table;
		List<CategoryName> names = getCategry(tableName);
		int size = names.size();
		for(int i =0;i<size;i++){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			String name = names.get(i).getName();
			String sql = "update "+tableName+" set amount = (select ifnull(sum(amount),0) from pay_income where incomecategory = '"+name+"') where name = '"+name+"'";
			db.execSQL(sql);
			db.close();
		}		
		
	}
	
}
