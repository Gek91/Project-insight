import { Component, Input, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProjectVersion, ProjectVersionStatusLabelMap } from '../../model/ProjectVersion';
import { ProjectsService } from '../../services/projects.service';

@Component({
  selector: 'app-project-version-history',
  templateUrl: './project-version-history.component.html',
  styleUrls: ['./project-version-history.component.css']
})
export class ProjectVersionHistoryComponent implements OnInit {

  @Input() versions? : ProjectVersion[];
  versionHistory$! : Observable<ProjectVersion[]>;

  public readonly PROJECT_VERSION_STATUS_LABEL : {[key: string]: string} = ProjectVersionStatusLabelMap;


  constructor() {
   }

  ngOnInit(): void {
    console.log(this.versions);
    if(this.versions != null) {
      this.versionHistory$ = of(this.versions);
    }
  }

}
