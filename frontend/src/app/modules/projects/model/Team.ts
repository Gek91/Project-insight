import { Customer } from "./Customer";

export interface Team {

    projectManager: Customer[];
    techLead: Customer[];
    developers: Customer[];
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