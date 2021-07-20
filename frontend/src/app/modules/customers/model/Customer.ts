import { Project, ProjectDeserializer } from "../../projects/model/Project";

export interface Customer {

    id: number;
    name: string;
    lastUpdateInstant: Date;
}

export interface CustomerSimple {

    id: number;
    name: string;
}

export interface CustomerDetail {

    id: number;
    name: string;
    creationInstant: Date;
    lastUpdateInstant: Date;
    projects: Project[];
}

export class CustomerDeserializer {

    public static fromCustomerSimple = (value: any) : CustomerSimple => (
        {
            id: value.id,
            name: value.name
        }
    )

    public static fromCustomerList = (value: any) : Customer[] => (
        value.customers?.map(
            (val: any) => CustomerDeserializer.fromCustomerListElement(val)
        )
    );

    public static fromCustomerListElement = (value: any) : Customer => (
        {
            id: value.id,
            name: value.name,
            lastUpdateInstant: new Date(value.lastUpdateInstant)
        }
    );

    public static fromCustomerDetail = (value: any) : CustomerDetail => (
        {
            id: value.id,
            name: value.name,
            lastUpdateInstant: new Date(value.lastUpdateInstant),
            creationInstant: new Date(value.creationInstant),
            projects: ProjectDeserializer.fromProjectList(value.projects)
        }
    );
}