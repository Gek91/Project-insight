import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ProjectDetail } from '../../model/Project';
import { ProjectsService } from '../../services/projects.service';

@Component({
  selector: 'app-projects-detail',
  templateUrl: './projects-detail.component.html',
  styleUrls: ['./projects-detail.component.css']
})
export class ProjectsDetailComponent implements OnInit {

  detail: ProjectDetail | null= null;

  constructor(
    private projectsService: ProjectsService,
    private route: ActivatedRoute,
    ) { }

  ngOnInit(): void {

    const id : string | null = this.route.snapshot.paramMap.get('projectId'); 
    if(id) {
      this.projectsService.getProjectDetail(id)
      .subscribe(
          project => this.detail = project
        );
    }
    
  }

}
