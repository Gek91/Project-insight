import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ProjectsDetailComponent } from "./components/projects-detail/projects-detail.component";
import { ProjectsListComponent } from "./components/projects-list/projects-list.component";


export const routes: Routes = [
    {
        path: '',  children: [
            { path: 'projects', component: ProjectsListComponent },
            { path: 'projects/:projectId', component: ProjectsDetailComponent },
            { path: '**', redirectTo: 'projects' }
        ]
    }
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class ProjectsRoutingModule { }