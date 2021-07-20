import { Component, Input, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Project } from 'src/app/modules/projects/model/Project';

@Component({
  selector: 'app-customer-project-list',
  templateUrl: './customer-project-list.component.html',
  styleUrls: ['./customer-project-list.component.css']
})
export class CustomerProjectListComponent implements OnInit {

  @Input() projects?: Project[];
  projects$! : Observable<Project[]>;

  constructor() { }

  ngOnInit(): void {
    if(this.projects != null) {
      this.projects$ = of(this.projects);
    }
  }

}
