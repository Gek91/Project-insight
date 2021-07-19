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
        map( (result: any) => ProjectDeserializer.fromProjectList(result))
      );

    //MOCK
    /*return of([
      {
        id: '1',
        name: 'project1',
        lastVersion: {
          majorVersion: 0,
          minorVerision: 0,
          patchVersion: 1,
          releaseDate: new Date("2019-01-16"),
        }
      }, 
      {
        id: '2',
        name: 'project2',
        lastVersion: {
          majorVersion: 1,
          minorVerision: 2,
          patchVersion: 3,
          releaseDate: new Date("2019-01-17"),
        }
      }, 
      {
        id: '3',
        name: 'project3',
        lastVersion: {
          majorVersion: 1,
          minorVerision: 0,
          patchVersion: 0,
          releaseDate: new Date("2019-01-18"),
        }
      }, 
    ])*/
  }

  getProjectDetail(id: string) : Observable<ProjectDetail> {

    return this.http.get(`/api/projects/${id}`).pipe(
      map( (result: any) => ProjectDeserializer.fromProjectDetail(result)),
      tap( value => console.log(value))
    );

    //MOCK
    /*switch(id) {

      case '1':
        return of({
          id: '1',
          name: 'project1',
          lastVersion: {
          majorVersion: 0,
          minorVerision: 0,
          patchVersion: 1,
          releaseDate: new Date("2019-01-16")
          },
          gcpUrl: "gcpUrl1",
          customer: {id: 1, name: "Unipol"},
          team: {
            projectManager: 'gino',
            techLead: 'pino',
            developers: ['rino', 'mimmo']
          },
          lastUpdate: new Date("2019-01-16")
        } as ProjectDetail);
        break;

      case '2':
        return of({
          id: '2',
          name: 'project2',
          lastVersion: {
            majorVersion: 1,
            minorVerision: 2,
            patchVersion: 3,
            releaseDate: new Date("2019-01-17"),
          },
          gcpUrl: "gcpUrl2",
          customer: {id: 1, name: "Etica"},
          team: {
            projectManager: 'gino',
            techLead: 'pino',
            developers: ['rino', 'mimmo']
          },
          lastUpdate: new Date("2019-01-17")
        } as ProjectDetail);
        break;

        case '3':
          return of({
            id: '3',
            name: 'project3',
            lastVersion: {
              majorVersion: 1,
              minorVerision: 0,
              patchVersion: 0,
              releaseDate: new Date("2019-01-18"),
            },
            gcpUrl: "gcpUrl2",
            customer: {id: 1, name: "Barilla"},
            team: {
              projectManager: 'gino',
              techLead: 'pino',
              developers: ['rino', 'mimmo']
            },
            lastUpdate: new Date("2019-01-18")
          } as ProjectDetail);
          break;
    }

    return of({} as ProjectDetail);*/
  }

  /*getProjectHistory(id : string ): Observable<ProjectVersion[]> {

    switch(id) {

      case '1':
       return of([
            {
              majorVersion: 0,
              minorVerision: 0,
              patchVersion: 1,
              releaseDate: new Date("2019-01-16")
              }
          ]);
          break;  

        case '2':
          return of([
            {
              majorVersion: 1,
              minorVerision: 2,
              patchVersion: 3,
              releaseDate: new Date("2019-01-17"),
            },
            {
              majorVersion: 1,
              minorVerision: 2,
              patchVersion: 2,
              releaseDate: new Date("2019-01-16"),
            },
            {
              majorVersion: 1,
              minorVerision: 2,
              patchVersion: 1,
              releaseDate: new Date("2019-01-15"),
            },
            {
              majorVersion: 1,
              minorVerision: 2,
              patchVersion: 0,
              releaseDate: new Date("2019-01-14"),
            }
          ]);
          break;

        case '3':
          return of([
            {
              majorVersion: 1,
              minorVerision: 0,
              patchVersion: 0,
              releaseDate: new Date("2019-01-18"),
            },
            {
              majorVersion: 0,
              minorVerision: 0,
              patchVersion: 1,
              releaseDate: new Date("2019-01-10"),
            }
          ])
          break;
    }

    return of([]);

  }*/
}
