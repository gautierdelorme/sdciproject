from mininet.topo import Topo

class TopoSdci( Topo ):

    def __init__( self ):
        "Create SDCI topo."

        # Initialize topology
        Topo.__init__( self )

        appserver = self.addDocker('appserver', ip='10.0.0.2', dimage="sdciproject/appserver")
	gi = self.addDocker('gi', ip='10.0.0.3', dimage="sdciproject/gi")
	gf1 = self.addDocker('gf1', ip='10.0.0.4', dimage="sdciproject/gf1")
	gf2 = self.addDocker('gf2', ip='10.0.0.5', dimage="sdciproject/gf2")
	gf3 = self.addDocker('gf3', ip='10.0.0.6', dimage="sdciproject/gf3")

        s1 = self.addSwitch( 's1' )
	s2 = self.addSwitch( 's2' )
	s3 = self.addSwitch( 's3' )
	s4 = self.addSwitch( 's4' )

        # Add links
        self.addLink( appserver, s1 )
        self.addLink( s1, s2 )
        self.addLink( s2, gi )
	self.addLink( s2, s3 )
	self.addLink( s2, s4 )
	self.addLink( s3, s4 )
	self.addLink( s3, gf1 )
	self.addLink( s3, gf2 )
	self.addLink( s4, gf3 )

topos = { 'toposdci': ( lambda: TopoSdci() ) }
