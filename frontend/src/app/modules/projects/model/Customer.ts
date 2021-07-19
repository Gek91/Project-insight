
export interface Customer {

    id: number;
    name: string;
}

export class CustomerDeserializer {

    public static fromCustomerSimple = (value: any) : Customer => (
        {
            id: value.id,
            name: value.name
        }
    )
}