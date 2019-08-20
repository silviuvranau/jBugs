import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BackendService} from '../core/backend/backend.service';
import {Observable} from 'rxjs';
import {User} from '../models/user.model';

@Component({
  selector: 'app-read-json',
  templateUrl: './read-json.component.html',
  styleUrls: ['./read-json.component.css']
})
export class ReadJsonComponent implements OnInit {

  title = 'JSON to Table Example';
  constructor(private backendService: BackendService) { }
  arrUsers: User[];

  ngOnInit() {
    this.getAllUsers();
  }
  getAllUsers() {
    this.backendService.get('http://localhost:8080/jbugs/api/users/').subscribe(
      (userList) => { this.arrUsers = userList; }
    );
  }
}