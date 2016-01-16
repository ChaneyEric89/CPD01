//
//  Post.m
//  UserData
//
//  Created by Eric Chaney on 1/15/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Post.h"
#import "ViewController.h"

@interface Post()

@end

@implementation Post
@synthesize postFieldBody;
@synthesize postFieldTitle;
@synthesize myTable;


- (void)viewDidLoad {
    
    [super viewDidLoad];
     PFUser *currentUser = [PFUser currentUser];
        myData = [[NSMutableArray alloc] init];
    
    
   query = [PFQuery queryWithClassName:@"Post"];
    [query whereKey:@"user" equalTo:currentUser];
    usersPosts = [query findObjects];
    
    
    int i;
    for (i = 0; i < [usersPosts count]; i++) {
        NSString *myArrayElement = [[usersPosts objectAtIndex:i] objectForKey:@"title"];
        [myData addObject:myArrayElement];
        NSLog(myArrayElement);
            }
    
//    myData = [[NSMutableArray alloc]initWithObjects:
//              @"Data 1 in array",@"Data 2 in array",@"Data 3 in array",
//              @"Data 4 in array",@"Data 5 in array",@"Data 5 in array",
//              @"Data 6 in array",@"Data 7 in array",@"Data 8 in array",
//              @"Data 9 in array", nil];
    // Do any additional setup after loading the view, typically from a nib.
    
    [myTable performSelectorOnMainThread:@selector(reloadData) withObject:nil waitUntilDone:YES];
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}




-(IBAction)logOut:(UIButton *)sender{
    [PFUser logOut];
    PFUser *currentUser = [PFUser currentUser]; // this will now be nil
//    ViewController* infoController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
//    [self.navigationController pushViewController:infoController animated:YES];
    [self moveToLogin];
}

-(IBAction)submitPost:(UIButton *)sender{
   PFUser *currentUser = [PFUser currentUser];
    //NSString *postBody = postFieldBody;
    
    PFObject *gameScore = [PFObject objectWithClassName:@"Post"];
    gameScore[@"body"] = postFieldBody.text;
    gameScore[@"title"] = postFieldTitle.text;
    gameScore[@"user"] = currentUser;
    gameScore.ACL = [PFACL ACLWithUser:[PFUser currentUser]];
    
   
    [gameScore saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (succeeded) {
            
            postFieldTitle.text = @"";
            postFieldBody.text = @"";
            NSLog(postFieldTitle.text);
        } else {
            // There was a problem, check error.description
        }
    }];
    
    
    //[self.myTable reloadData];
    
//    dispatch_async(dispatch_get_main_queue(), ^{
//        [self.myTable reloadData];
//    });

    [myTable performSelectorOnMainThread:@selector(reloadData) withObject:nil waitUntilDone:YES];
    
}


-(void)moveToLogin{
    UIStoryboard *mainStoryboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    Post *destViewController = [mainStoryboard instantiateViewControllerWithIdentifier:@"viewController"];
    [self.navigationController pushViewController:destViewController animated:YES];
    [[UIApplication sharedApplication].keyWindow setRootViewController:destViewController];
}




- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:
(NSInteger)section{
    return [myData count]/2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:
(NSIndexPath *)indexPath{
    static NSString *cellIdentifier = @"cellID";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:
                             cellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]initWithStyle:
                UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    NSString *stringForCell;
    if (indexPath.section == 0) {
        stringForCell= [myData objectAtIndex:indexPath.row];
        
    }
    else if (indexPath.section == 1){
        stringForCell= [myData objectAtIndex:indexPath.row+ [myData count]/2];
        
    }
    [cell.textLabel setText:stringForCell];
    return cell;
}

// Default is 1 if not implemented
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 2;
}



-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:
(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    NSLog(@"Section:%d Row:%d selected and its data is %@",
          indexPath.section,indexPath.row,cell.textLabel.text);
    
   // [[usersPosts objectAtIndex:indexPath] deleteInBackground];
    
//    PFObject *getPost = [PFObject objectWithClassName:@"Post"];
//    [getPost deleteInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
//        if (succeeded) {
//           [[getPost objectAtIndex:indexPath] deleteInBackground];
//        } else {
//            // There was a problem, check error.description
//        }
//    }];
    
   
    NSInteger i= indexPath.section;
    
    
    PFQuery *query = [PFQuery queryWithClassName:@"Post"];
    PFUser *currentUser = [PFUser currentUser];
    [query whereKey:@"user" equalTo:currentUser];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
           
            // Do something with the found objects
            [PFObject deleteAllInBackground:[objects objectAtIndex:i] ];
        } else {
            // Log details of the failure
            NSLog(@"Error: %@ %@", error, [error userInfo]);
        }
    }];
    
    
}


@end
