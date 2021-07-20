
export interface Employee {

    id: number;
    name: string;
    surname:string;
}

export class EmployeeDeserializer {

    public static fromEmployeeSimple = (value: any) : Employee => (
        {
            id: value.id,
            name: value.name,
            surname: value.surname
        }
    )
}