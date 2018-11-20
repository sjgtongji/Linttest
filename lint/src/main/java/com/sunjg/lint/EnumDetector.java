package com.sunjg.lint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UEnumConstant;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class EnumDetector extends Detector implements Detector.UastScanner {
  private static final Class<? extends Detector> DETECTOR_CLASS = EnumDetector.class;
  private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;

  private static final Implementation IMPLEMENTATION = new Implementation(
    DETECTOR_CLASS,
    DETECTOR_SCOPE
  );
  private static final String ISSUE_ID = "Enum";
  private static final String ISSUE_DESCRIPTION = "Avoid Using Enums";
  private static final String ISSUE_EXPLANATION = "No real Android programmer should ever use enums. EVER.";
  private static final Category ISSUE_CATEGORY = Category.PERFORMANCE;
  private static final int ISSUE_PRIORITY = 5;
  private static final Severity ISSUE_SEVERITY = Severity.WARNING;

  public static final Issue ISSUE = Issue.create(
    ISSUE_ID,
    ISSUE_DESCRIPTION,
    ISSUE_EXPLANATION,
    ISSUE_CATEGORY,
    ISSUE_PRIORITY,
    ISSUE_SEVERITY,
    IMPLEMENTATION  // This was defined in the "Implementations" section
  );

  @Override
  public EnumSet<Scope> getApplicableFiles() {
    return Scope.JAVA_FILE_SCOPE;
  }
  @Override
  public List<Class<? extends UElement>> getApplicableUastTypes() {
    return Collections.singletonList(UEnumConstant.class);
  }

  @Override
  public UElementHandler createUastHandler(JavaContext context) {
    // Not: Visiting UAST nodes is a pretty general purpose mechanism;
    // Lint has specialized support to do common things like "visit every class
    // that extends a given super class or implements a given interface", and
    // "visit every call site that calls a method by a given name" etc.
    // Take a careful look at UastScanner and the various existing lint check
    // implementations before doing things the "hard way".
    // Also be aware of context.getJavaEvaluator() which provides a lot of
    // utility functionality.
    return new UElementHandler() {

      @Override
      public void visitEnumConstant(UEnumConstant node) {
        context.report(ISSUE, node, context.getLocation(node),
          "This code mentions `enum`:" + node.asLogString());
      }
    };
  }
}
