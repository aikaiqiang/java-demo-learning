package com.kaywall.design.factory;

import javax.swing.*;
import java.awt.*;

/**
 *  具体产品1
 * @author aikaiqiang
 * @date 2019年10月11日 14:52
 */
public class ConcreteProduct1 implements Product {

	JScrollPane sp;
	JFrame jf = new JFrame("工厂方法模式测试");

	public ConcreteProduct1() {

		Container contentPane = jf.getContentPane();
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,1));
		p1.setBorder(BorderFactory.createTitledBorder("产品1：🐮🐮🐮🐮🐮🐮🐮🐮🐮🐮"));
		sp = new JScrollPane(p1);
		contentPane.add(sp, BorderLayout.CENTER);
		JLabel l1 = new JLabel(new ImageIcon("pattern/design-patterns/src/main/resources/pic/cattle.jpg"));
		p1.add(l1);
		jf.pack();
		jf.setVisible(false);
		// 用户点击窗口关闭
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void show() {
		System.out.println("展示产品1");
		jf.setVisible(true);
	}
}
