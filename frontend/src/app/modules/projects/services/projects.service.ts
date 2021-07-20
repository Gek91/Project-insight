import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { observable, Observable, ObservableInput, of } from 'rxjs';
import { Project, ProjectDeserializer, ProjectDetail } from '../model/Project';
import { ProjectVersion } from '../model/ProjectVersion';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  constructor(private http: HttpClient) { }

  getProjectsList() : Observable<Project[]> {

    return this.http.get("/api/projects")
      .pipe(
        map( (result: any) => ProjectDeserializer.fromProjectList(result.projects))
      );
  }

  getProjectDetail(id: string) : Observable<ProjectDetail> {

    return this.http.get(`/api/projects/${id}`)
      .pipe(
        map( (result: any) => ProjectDeserializer.fromProjectDetail(result))
      );
  }
}
