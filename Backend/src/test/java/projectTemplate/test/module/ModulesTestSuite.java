package projectTemplate.test.module;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import projectTemplate.test.module.project.ProjectModuleTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ProjectModuleTestSuite.class
})
public class ModulesTestSuite {
}
