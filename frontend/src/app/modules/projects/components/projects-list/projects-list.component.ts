import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Project } from '../../model/Project';
import { ProjectsService } from '../../services/projects.service';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.css']
})
export class ProjectsListComponent implements OnInit {

  projects$!: Observable<Project[]>;

  constructor(
    private projectsService: ProjectsService,
    public router: Router)
     { }

  ngOnInit(): void {

    this.projects$ = this.projectsService.getProjectsList();
  }

}
