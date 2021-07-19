import { Component, Input, OnInit } from '@angular/core';
import { Team } from '../../model/Team';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css']
})
export class TeamDetailComponent implements OnInit {

  @Input() team?: Team;

  constructor() { }

  ngOnInit(): void {
  }

}
