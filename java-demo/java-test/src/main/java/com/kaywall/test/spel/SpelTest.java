package com.kaywall.test.spel;


import org.springframework.expression.*;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年08月22日 16:04
 */
public class SpelTest {

	public static void main(String[] args) {
		String greetingExp = "Hello, #{#user}";
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("user", "Kaywall");

		Expression expression = parser.parseExpression(greetingExp,
				new TemplateParserContext());
		System.out.println(expression.getValue(context, String.class));

		Writer writer = new Writer();
		writer.setName("Writer's name");
		StandardEvaluationContext modifierContext = new StandardEvaluationContext();
		modifierContext.setVariable("name", "Overriden writer's name");
		parser.parseExpression("name = #name").getValue(modifierContext);
		System.out.println("writer's name is : " + writer.getName());
	}

	private static class Writer{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
