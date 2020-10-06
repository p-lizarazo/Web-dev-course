import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForumListComponent } from './forum/forum-list/forum-list.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';
import { AppComponent } from './app.component';
import { LoginComponent } from './forum/login/login.component';

const routes: Routes = [

  { path: '', component: LoginComponent},
  { path: 'forum/list', component: ForumListComponent},
  { path: ':forumId', component: TopicListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
