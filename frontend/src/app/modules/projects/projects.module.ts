import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsListComponent } from './components/projects-list/projects-list.component';
import { HttpClientModule } from '@angular/common/http';
import { ProjectsDetailComponent } from './components/projects-detail/projects-detail.component';
import { ProjectsRoutingModule } from './projects-routing.module';
import { TeamDetailComponent } from './components/team-detail/team-detail.component';
import { ProjectVersionHistoryComponent } from './components/project-version-history/project-version-history.component';
import {MatTableModule} from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';

@NgModule({
  declarations: [
    ProjectsListComponent,
    ProjectsDetailComponent,
    TeamDetailComponent,
    ProjectVersionHistoryComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ProjectsRoutingModule,
    MatTableModule,
    MatCardModule
  ],
  exports: [
    ProjectsListComponent,
    MatTableModule,
    MatCardModule
  ]
})
export class ProjectsModule { }
