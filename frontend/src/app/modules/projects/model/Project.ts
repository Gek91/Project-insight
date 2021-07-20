import { NonNullAssert } from "@angular/compiler";
import { Customer, CustomerDeserializer, CustomerSimple } from "../../customers/model/Customer";
import { ProjectVersion, ProjectVersionDeserializer, ProjectVersionSimple } from "./ProjectVersion";
import { Team, TeamDeserializer } from "./Team";

export interface Project{ 

    id: string;
    name: string;
    description: string,
    customer: CustomerSimple,
    creationInstant: Date,
    lastUpdateInstant: Date
    lastVersion: ProjectVersionSimple | null
}

export interface ProjectDetail {

    id: string;
    name: string;
    description: string,
    customer: CustomerSimple,
    creationInstant: Date,
    lastUpdateInstant: Date
    team: Team;
    versions: ProjectVersion[]
}

export class ProjectDeserializer {

    public static fromProjectList = (value : any) : Project[] => (
        value?.map( 
            (val: any) => ProjectDeserializer.fromProjectListElement(val)
        )
    );
    
    public static fromProjectListElement = (value : any) : Project => (
        {
            id: value.id,
            name: value.name,
            description: value.description,
            customer: CustomerDeserializer.fromCustomerSimple(value.customer),
            creationInstant: new Date(value.creationInstant),
            lastUpdateInstant: new Date(value.lastUpdateInstant),
            lastVersion: value.lastProjectVersion != null ? ProjectVersionDeserializer.fromProjectVersionSimple(value.lastProjectVersion) : null
        }
    );

    public static fromProjectDetail = (value: any) : ProjectDetail => (
        {
            id: value.id,
            name: value.name,
            description: value.description,
            customer: CustomerDeserializer.fromCustomerSimple(value.customer),
            creationInstant: new Date(value.creationInstant),
            lastUpdateInstant: new Date(value.lastUpdateInstant),
            team: TeamDeserializer.fromTeam(value.team),
            versions: ProjectVersionDeserializer.fromProjectVersionList(value.versions)
        }
    );
}