import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CustomersDetailComponent } from "./components/customers-detail/customers-detail.component";
import { CustomersListComponent } from "./components/customers-list/customers-list.component";


export const routes: Routes = [
    {
        path: '',  children: [
            { path: 'customers', component: CustomersListComponent },
            { path: 'customers/:customerId', component: CustomersDetailComponent },
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
export class CustomersRoutingModule { }