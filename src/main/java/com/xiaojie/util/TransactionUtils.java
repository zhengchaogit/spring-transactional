package com.xiaojie.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * @author Administrator
 *编程事务类，手动开始，手动提交，手动回滚
 */
@Component
@Scope("prototype")
public class TransactionUtils {
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
		//开始事务
		public TransactionStatus begin() {
			TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
			return transaction;
		}
		//提交事务
		public void commit(TransactionStatus transaction) {
			dataSourceTransactionManager.commit(transaction);
		}
		//回滚事务
		public void rollback(TransactionStatus status) {
			System.out.println("事务回滚。。。。。。");
			dataSourceTransactionManager.rollback(status);
		}
}
