using System;
using Microsoft.Azure.EventHubs;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace EventListener
{
    class Program
    {
        private static EventHubClient eventHubClient;
        private const string EhConnectionString = "Endpoint=sb://matrix4entry.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=ZnAA8gP+43y/7oPeIX4z7jKNcOHyh0RA6PB7GtNAdws=";
        private const string EhEntityPath = "mytollentryeventhub";
        private static List<VehicleItem> items = new List<VehicleItem>();

        static void Main(string[] args)
        {
          
            MainAsync(args).GetAwaiter().GetResult();
        }

        public static void LoadJson(String fullPath)
        {
            using (StreamReader r = File.OpenText(fullPath))
            {
                string json = r.ReadToEnd();
                items = JsonConvert.DeserializeObject<List<VehicleItem>>(json);               
            }
        }
        private static async Task MainAsync(string[] args)
        {
            // Creates an EventHubsConnectionStringBuilder object from the connection string, and sets the EntityPath.
            // Typically, the connection string should have the entity path in it, but for the sake of this simple scenario
            // we are using the connection string from the namespace.
            var connectionStringBuilder = new EventHubsConnectionStringBuilder(EhConnectionString)
            {
                EntityPath = EhEntityPath
            };

            eventHubClient = EventHubClient.CreateFromConnectionString(connectionStringBuilder.ToString());
            //watch();
            FileSystemWatcher watcher = new FileSystemWatcher();
            watcher.Path = @"C:\Amitha NEU\Engg Big Data\Final\SAJ\output";
            watcher.NotifyFilter = NotifyFilters.LastWrite;
            watcher.Filter = "*.*";
            watcher.Changed += new FileSystemEventHandler(OnChanged);
            watcher.EnableRaisingEvents = true;
            // LoadJson();
            //await SendJsonObjectsToEventHub();
            await eventHubClient.CloseAsync();

            //Console.WriteLine("Press Enter");
            Console.ReadLine();
        }

        private static void watch()
        {
            FileSystemWatcher watcher = new FileSystemWatcher();
            watcher.Path = @"C:\Amitha NEU\Engg Big Data\Final\SAJ\output";
            watcher.NotifyFilter = NotifyFilters.LastWrite;
            watcher.Filter = "*.*";
            watcher.Changed += new FileSystemEventHandler(OnChanged);
            watcher.EnableRaisingEvents = true;
        }

        private static async void OnChanged(object source, FileSystemEventArgs e)
        {
            Console.WriteLine("OnChanged");
            String fullPath = e.FullPath;
            LoadJson(fullPath);
            await SendJsonObjectsToEventHub();
        }
        // Creates an Event Hub client and sends 100 messages to the event hub.
        private static async Task SendJsonObjectsToEventHub()
        {
            foreach (VehicleItem item in items)
            {              
                      
                try
                {
                    Console.WriteLine(item.Manufacturer);
                    Console.WriteLine(item.MakeAndModel);
                    Console.WriteLine(item.LicensePlatePK);
                    Console.WriteLine(item.LicensePlateRK);
                    var jsonItem = "{ \"" + "Manufacturer" + "\" : \"" + item.Manufacturer + "\" ," +
                        " \"" + "MakeAndModel" + "\" : \"" + item.MakeAndModel + "\"  , " +
                        "\"" + "licenseplatePK" + "\" : \"" + item.LicensePlatePK + "\" ," +
                        " \"" + "licenseplateRK" + "\" : \"" + item.LicensePlateRK + "\"}";
                    
                    await eventHubClient.SendAsync(new EventData(Encoding.UTF8.GetBytes(jsonItem)));
                  
                }
                catch (Exception exception)
                {
                    Console.WriteLine($"{DateTime.Now} > Exception: {exception.Message}");
                }

                //await Task.Delay(10);
            }

        }
       
    }
}