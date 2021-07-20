import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Customer, CustomerDeserializer, CustomerDetail } from '../model/Customer';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  constructor(private http: HttpClient) { }


  getCustomerList() : Observable<Customer[]> {

    return this.http.get("/api/customers")
      .pipe(
        map( (result: any) => CustomerDeserializer.fromCustomerList(result))
      )
  }

  getCustomerDetail(id: string) : Observable<CustomerDetail> {

    return this.http.get(`/api/customers/${id}`)
      .pipe(
        map( (result:any) => CustomerDeserializer.fromCustomerDetail(result))
      );
  }
}
