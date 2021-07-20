import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CustomerDetail } from '../../model/Customer';
import { CustomersService } from '../../services/customers.service';

@Component({
  selector: 'app-customers-detail',
  templateUrl: './customers-detail.component.html',
  styleUrls: ['./customers-detail.component.css']
})
export class CustomersDetailComponent implements OnInit {

  detail: CustomerDetail | null = null

  constructor(
    private customerService: CustomersService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {

    const id : string | null = this.route.snapshot.paramMap.get('customerId'); 

    if(id) {
      this.customerService.getCustomerDetail(id)
      .subscribe(
        customer => {
          this.detail = customer;
        }
      );
    }
  }

}
