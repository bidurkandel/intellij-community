package com.jetbrains.python.refactoring;

import com.intellij.psi.PsiElement;
import com.jetbrains.python.fixtures.LightMarkedTestCase;
import com.jetbrains.python.psi.PyReferenceExpression;
import com.jetbrains.python.refactoring.inline.PyInlineLocalHandler;

import java.util.Map;

/**
 * @author Dennis.Ushakov
 */
public class PyInlineLocalTest extends LightMarkedTestCase {
  private void doTest() throws Exception {
    doTest(null);
  }

  private void doTest(String expectedError) throws Exception {
    final String name = getTestName(true);
    final Map<String,PsiElement> map = configureByFile("/refactoring/inlinelocal/" + name + ".before.py");
    try {
      PsiElement element = map.values().iterator().next().getParent();
      if (element instanceof PyReferenceExpression) element = ((PyReferenceExpression)element).resolve();
      PyInlineLocalHandler.inline(myFixture.getProject(), myFixture.getEditor(), element);
      if (expectedError != null) fail("expected error: '" + expectedError + "', got none");
    }
    catch (Exception e) {
      assertEquals(expectedError, e.getMessage());
      return;
    }
    myFixture.checkResultByFile("/refactoring/inlinelocal/" + name + ".after.py");
  }

  public void testSimple() throws Exception {
    doTest();
  }

  public void testPriority() throws Exception {
    doTest();
  }

  public void testNoDominator() throws Exception {
    doTest("Cannot find a single definition to inline.");
  }

  public void testMultiple() throws Exception {
    doTest();
  }
}
