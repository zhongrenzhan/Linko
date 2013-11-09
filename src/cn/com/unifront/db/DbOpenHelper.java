package cn.com.unifront.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	public DbOpenHelper(Context context) {
		super(context, "licai.db", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/* 表1 ：userpsw 登陆信息表 */
		db.execSQL("create table if not exists userpsw(userId integer primary key autoincrement,psw text,pritect_state text)");

		/* 表4 ：account 账户表 */
		db.execSQL("create table if not exists account(id integer primary key autoincrement,balance text not null default 0,warm text default 0,warm_balance text default 0,currency_id text default 0 ,name text,FOREIGN  KEY (id) REFERENCES incomecategory(account_id))");
		
		/* 表2 ：incomecategory 收入类型表 */
		db.execSQL("create table if not exists incomecategory(in_categoryId integer primary key autoincrement,name text,amount float default 0 ,account_id text,remark text )");

		/* 表3 ：payoutcategory 消费类型表 */
		db.execSQL("create table if not exists payoutcategory(pay_categoryId integer primary key autoincrement,name text,amount float default 0,account_id text,remark text )");

		/* 表5 ：currency 货币种类表 */ 
		db.execSQL("create table if not exists currency(currency_Id integer primary key autoincrement,name text,sign text)");
		
		/* 表6 ：pay_income 收支表 */
		db.execSQL("create table if not exists pay_income(in_pay_Id integer primary key autoincrement,type text , amount float,time datetime,account string ,incomecategory string ,describe text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
