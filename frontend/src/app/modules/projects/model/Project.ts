import { Customer, CustomerDeserializer } from "./Customer";
import { ProjectVersion, ProjectVersionDeserializer, ProjectVersionSimple } from "./ProjectVersion";
import { Team, TeamDeserializer } from "./Team";

export interface Project{ 

    id: string;
    name: string;
    description: string,
    customer: Customer,
    creationInstant: Date,
    lastUpdateInstant: Date
    lastVersion: ProjectVersionSimple
}

export interface ProjectDetail {

    id: string;
    name: string;
    description: string,
    customer: Customer,
    creationInstant: Date,
    lastUpdateInstant: Date
    team: Team;
    versions: ProjectVersion[]
}

export class ProjectDeserializer {

    public static fromProjectList = (value : any) : Project[] => (
        value.projects?.map( 
            (val: any) => ProjectDeserializer.fromProjectListElement(val)
        )
    );
    
    public static fromProjectListElement = (value : any) : Project => (
        {
            id: value.id,
            name: value.name,
            description: value.description,
            customer: CustomerDeserializer.fromCustomerSimple(value.customer),
            creationInstant: value.creationInstant,
            lastUpdateInstant: value.lastUpdateInstant,
            lastVersion: ProjectVersionDeserializer.fromProjectVersionSimple(value.lastProjectVersion)
        }
    );

    public static fromProjectDetail = (value: any) : ProjectDetail => (
        {
            id: value.id,
            name: value.name,
            description: value.description,
            customer: CustomerDeserializer.fromCustomerSimple(value.customer),
            creationInstant: value.creationInstant,
            lastUpdateInstant: value.lastUpdateInstant,
            team: TeamDeserializer.fromTeam(value.team),
            versions: ProjectVersionDeserializer.fromProjectVersionList(value.versions)
        }
    );
}