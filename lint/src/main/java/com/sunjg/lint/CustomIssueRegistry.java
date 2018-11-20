package com.sunjg.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: CustomIssueRegistry
 * @Description: 自定义lint规则入口
 * @Author: jigangsun
 * @Date: 2018/11/20 下午2:37
 */
public class CustomIssueRegistry extends IssueRegistry {
  private List<Issue> mIssues = Arrays.asList(
    EnumDetector.ISSUE   // Could totally add more here
  );
  public CustomIssueRegistry(){

  }
  @Override
  public List<Issue> getIssues() {
    return mIssues;
  }
  @Override
  public int getApi() { return com.android.tools.lint.detector.api.ApiKt.CURRENT_API; }
}
