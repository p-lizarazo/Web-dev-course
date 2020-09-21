import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForumListComponent } from './forum/forum-list/forum-list.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';

const routes: Routes = [
  { path: '', component: ForumListComponent },
  { path: ':forumId', component: TopicListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
