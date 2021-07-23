package projectTemplate.test.module.project;

import org.junit.Assert;
import org.junit.Test;
import projectinsight.module.app.commons.UIIDGenerator;
import projectinsight.module.project.ProjectGuiceModule;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectTemplate.test.base.BaseUnitTest;

import java.util.Arrays;
import java.util.Collections;

public class ProjectTest extends BaseUnitTest {

  public ProjectTest() {
    super(Collections.singletonList(ProjectGuiceModule.class));
  }

  /*@Test
  public void testCreateProject() {

    ProjectBuilder builder = Project.getBuilder();

    String customerId = UIIDGenerator.generate();
    String dev1Id = UIIDGenerator.generate();
    String dev2Id = UIIDGenerator.generate();
    String projectManagerId = UIIDGenerator.generate();
    String techLeadId = UIIDGenerator.generate();

    builder.setName("project name");
    builder.setDevelopersIds(Arrays.asList(dev1Id, dev2Id));
    builder.setTechLeadIds(Collections.singletonList(techLeadId));
    builder.setProjectManagerIds(Collections.singletonList(projectManagerId));
    builder.setCustomerId(customerId);

    Project project = builder.buildProject();

    Assert.assertNotNull(project.getId());
    Assert.assertNotNull(project.getCreationInstant());
    Assert.assertNotNull(customerId, project.getLastUpdateInstant());

    Assert.assertEquals("project name", project.getName());
    Assert.assertEquals(customerId, project.getCustomerId());
    Assert.assertEquals(1 , project.getTeam().getProjectManagerIds().size());
    Assert.assertEquals(projectManagerId , project.getTeam().getProjectManagerIds().get(0));
    Assert.assertEquals(1 , project.getTeam().getTechLeadIds().size());
    Assert.assertEquals(techLeadId , project.getTeam().getTechLeadIds().get(0));
    Assert.assertEquals(2 , project.getTeam().getDevelopersIds().size());
    Assert.assertEquals(dev1Id , project.getTeam().getDevelopersIds().get(0));
    Assert.assertEquals(dev2Id , project.getTeam().getDevelopersIds().get(1));

    //Assert.assertFalse(project.getLastProjectVersion().isPresent());
    //Assert.assertEquals(0 , project.getVersions().size());

  }*/
}
