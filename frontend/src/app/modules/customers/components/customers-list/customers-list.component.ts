import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Customer } from '../../model/Customer';
import { CustomersService } from '../../services/customers.service';

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.css']
})
export class CustomersListComponent implements OnInit {

  customers$!: Observable<Customer[]>;

  
  constructor(private customerService: CustomersService,
    public router: Router) { }

  ngOnInit(): void {

    this.customers$ = this.customerService.getCustomerList();
  }

}
