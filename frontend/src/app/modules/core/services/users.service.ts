import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  //TODO: FIX ME
  //currentUsers: Subject<User> = new BehaviorSubject<User>(null); //null not a valid value
  currentUsers: Subject<User> = new BehaviorSubject<any>(null);


  constructor() { }

  public setCurrentUser(newUser: User): void {
    this.currentUsers.next(newUser);
  }
}
