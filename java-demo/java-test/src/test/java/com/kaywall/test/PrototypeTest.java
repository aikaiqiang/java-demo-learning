package com.kaywall.test;

import com.kaywall.test.prototype.ShoppingCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年08月22日 17:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath*:applicationContext-test.xml" })
public class PrototypeTest {

	@Autowired
	private BeanFactory beanFactory;

	@Test
	public void test() {
		ShoppingCart cart1 = (ShoppingCart) beanFactory.getBean("shoppingCart");
		assertTrue("Id of cart1 should be 9 but was "+cart1.getId(),
				cart1.getId() == 9);
		cart1.setId(100);
		ShoppingCart cart2 = (ShoppingCart) beanFactory.getBean("shoppingCart");
		assertTrue("Id of cart2 should be 9 but was "+cart2.getId(),
				cart2.getId() == 9);
		assertTrue("Id of second cart ("+cart2.getId()+") shouldn't be the same as the first one: "+cart1.getId(),
				cart1.getId() == cart2.getId());
		cart2.setId(cart1.getId());
		assertTrue("Now (after cart2.setId(cart1.getId())), the id of second cart ("+cart2.getId()+") should be the same as the first one: "
				+cart1.getId(), cart1.getId() == cart2.getId());
		assertTrue("Both instance shouldn't be the same", cart1 != cart2);
	}
}
