import { Employee } from "./Employee";

export interface Team {

    projectManager: Employee[];
    techLead: Employee[];
    developers: Employee[];
}

export class TeamDeserializer {

    public static fromTeam = (value : any) : Team => (
        {
            projectManager: value.projectManager,
            techLead: value.techLead,
            developers: value.developers
        }
    );
}