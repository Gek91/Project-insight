import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomersRoutingModule } from './customers-routing.module';
import { CustomersDetailComponent } from './components/customers-detail/customers-detail.component';
import { CustomersListComponent } from './components/customers-list/customers-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import {MatGridListModule} from '@angular/material/grid-list';
import { CustomerProjectListComponent } from './components/customer-project-list/customer-project-list.component';


@NgModule({
  declarations: [
    CustomersDetailComponent,
    CustomersListComponent,
    CustomerProjectListComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    CustomersRoutingModule,
    MatTableModule,
    MatCardModule,
    MatGridListModule
  ],
  exports: [
    
  ]
})
export class CustomersModule { }
